import NoSubscriptionMessage from "@/components/widgets/NoSubscriptionMessage";
import { useSendMessageMutation } from "@/config/api/apiSlice";
import { setHasSubscription, setPageTitle } from "@/config/store/generalSlice";
import { AppDispatch, RootState } from "@/config/store/store";
import { Bot } from "lucide-react";
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";

type Message = { sender: string; text: string };

const ConsultationPage: React.FC = () => {
  const dispatch: AppDispatch = useDispatch();
  const [sendMessage, { isLoading }] = useSendMessageMutation();
  const [subscriptionChecked, setSubscriptionChecked] = useState(false);
  const hasSubscription = useSelector(
    (state: RootState) => state.general.hasSubscription
  );

  useEffect(() => {
    dispatch(setPageTitle("Healthcare Assistant"));
  }, [dispatch]);

  useEffect(() => {
    const checkSubscription = async () => {
      const response = await sendMessage({ userInput: "hello" }).unwrap();
      console.log("initial response ", response);
      if (response.status === "error") {
        localStorage.removeItem("hasSubscription");
        dispatch(setHasSubscription(false));
      }
      setSubscriptionChecked(true);
    };
    checkSubscription();
  }, [sendMessage, dispatch]);

  const [messages, setMessages] = useState<Message[]>([
    { sender: "Assistant", text: "How can I assist you today?" },
  ]);
  const [input, setInput] = useState("");

  const handleSend = async () => {
    if (input.trim()) {
      const newMessages: Message[] = [
        ...messages,
        { sender: "You", text: input },
      ];
      setMessages(newMessages);
      setInput("");

      try {
        console.log("request: ", input);
        const response = await sendMessage({ userInput: input }).unwrap();
        console.log("response: ", response);

        setMessages([
          ...newMessages,
          {
            sender: "Assistant",
            text: response.response,
          },
        ]);
      } catch (err) {
        console.error("Error sending message:", err);
        setMessages([
          ...newMessages,
          { sender: "Assistant", text: "An error occurred. Please try again." },
        ]);
      } finally {
        setInput("");
      }
    }
  };

  if (!subscriptionChecked) {
    return (
      <div>
        <div className="flex items-center justify-center">
          <div className="w-8 h-8 border-4 border-blue-500 border-t-transparent rounded-full animate-spin"></div>
        </div>
      </div>
    );
  }

  return (
    <>
      {hasSubscription ? (
        <div className="flex flex-col h-full">
          {/* Conversation Area */}
          <div className="flex flex-col flex-1 overflow-y-auto p-4 bg-gray-100 rounded-md">
            {messages.map((message, index) => (
              <div
                key={index}
                className={`mb-4 ${message.sender === "You" ? "self-end" : ""}`}
              >
                <div className="flex items-center gap-2">
                  <div>
                    <strong>{message.sender === "You" ? "" : <Bot />}</strong>
                  </div>
                  <div
                    className={`p-4 rounded-lg shadow-sm ${
                      message.sender === "You" ? "bg-teal-100" : "bg-white"
                    }`}
                  >
                    <p className="text-gray-800">{message.text}</p>
                  </div>
                </div>
              </div>
            ))}
          </div>

          {/* Input Area */}
          <div className="bg-white border-gray-300">
            <div className="flex items-center space-x-2 mt-4">
              <textarea
                rows={1}
                className="flex-1 resize-none border border-gray-300 rounded-lg p-2 focus:ring-2 focus:ring-teal-300"
                placeholder="Type your question here..."
                value={input}
                onChange={(e) => setInput(e.target.value)}
                onKeyDown={(e) => {
                  if (e.key === "Enter" && !e.shiftKey) {
                    e.preventDefault();
                    handleSend();
                  }
                }}
              ></textarea>
              <button
                className="bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-teal-600"
                onClick={handleSend}
              >
                {isLoading ? "Sending..." : "Send"}
              </button>
            </div>
          </div>
        </div>
      ) : (
        <div className="h-full">
          <NoSubscriptionMessage />
        </div>
      )}
    </>
  );
};

export default ConsultationPage;
