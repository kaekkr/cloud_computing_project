import React from "react";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardHeader,
  CardContent,
  CardFooter,
} from "@/components/ui/card";
import { AlertCircle } from "lucide-react";
import { useSubscribeMutation } from "@/config/api/apiSlice";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "@/config/store/store";
import { setHasSubscription } from "@/config/store/generalSlice";
import { useToast } from "@/hooks/use-toast";

const NoSubscriptionMessage: React.FC = () => {
  const [subscribe, { isLoading }] = useSubscribeMutation();
  const username =
    useSelector((state: RootState) => state.auth.username) || "Alex";
  const dispatch = useDispatch();

  const { toast } = useToast();

  const handleSubscribe = async () => {
    try {
      await subscribe({ username }).unwrap();
      toast({
        title: "Subscription successful!",
      });
      dispatch(setHasSubscription(true));
    } catch (error) {
      console.error("Subscription failed:", error);
      toast({
        title: "Subscription failed",
        variant: "destructive",
      });
    }
  };
  return (
    <div className="flex items-center justify-center h-full bg-gray-100">
      <Card className="max-w-md w-full bg-white shadow-lg rounded-lg">
        <CardHeader className="flex items-center justify-center bg-red-50 p-4 rounded-lg rounded-b-none">
          <AlertCircle className="w-8 h-8 text-red-500" />
        </CardHeader>
        <CardContent className="text-center p-6">
          <h2 className="text-xl font-semibold text-gray-800">
            Subscription Required
          </h2>
          <p className="mt-2 text-gray-600">
            To use the Virtual Healthcare Assistant, you need an active
            subscription. Unlock premium features and access personalized
            healthcare assistance today.
          </p>
        </CardContent>
        <CardFooter className="flex justify-center p-4">
          <Button
            className="w-full bg-blue-600 hover:bg-blue-700 text-white"
            onClick={handleSubscribe}
            disabled={isLoading}
          >
            {isLoading ? "Loading..." : "Subscribe Now"}
          </Button>
        </CardFooter>
      </Card>
    </div>
  );
};

export default NoSubscriptionMessage;
