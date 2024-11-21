import { useLoginUserMutation } from "@/config/api/apiSlice";
import { login } from "@/config/store/authSlice";
import { useToast } from "@/hooks/use-toast";
import { SerializedError } from "@reduxjs/toolkit";
import { FetchBaseQueryError } from "@reduxjs/toolkit/query";
import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { Link, useNavigate } from "react-router-dom";

const isFetchBaseQueryError = (
  error: FetchBaseQueryError | SerializedError
): error is FetchBaseQueryError => {
  return "status" in error;
};

const isApiError = (data: unknown): data is ApiError => {
  return typeof data === "object" && data !== null && "message" in data;
};

interface ApiError {
  message: string;
}
const AuthPage: React.FC = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loginUser, { isLoading, isError, error }] = useLoginUserMutation();
  const dispatch = useDispatch();
  const { toast } = useToast();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      console.log("data: ", { username, password });
      const response = await loginUser({ username, password }).unwrap();
      console.log("response: ", response);
      dispatch(
        login({
          username: response.username,
          token: response.token,
        })
      );
      toast({
        title: "Login successful!",
      });
      navigate("/dashboard");
    } catch (err) {
      console.error("err: ", err);
      toast({
        title: "Login failed. Please check your credentials.",
        variant: "destructive",
      });
      setPassword("");
    }
  };

  return (
    <div className="min-h-screen bg-white flex items-center justify-center">
      <div className="w-full max-w-md bg-gray-100 p-8 rounded-lg shadow-lg">
        <h2 className="text-2xl font-bold text-blue-400 text-center mb-6">
          Sign In
        </h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block text-gray-700 mb-2">Username</label>
            <input
              type="text"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-300"
              placeholder="Enter your username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700 mb-2">Password</label>
            <input
              type="password"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-300"
              placeholder="Enter your password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button
            type="submit"
            disabled={isLoading}
            className={`w-full bg-teal-500 text-white py-2 rounded-lg hover:bg-teal-600 ${
              isLoading ? "opacity-50 cursor-not-allowed" : ""
            }`}
          >
            {isLoading ? "Signing In..." : "Sign In"}
          </button>
        </form>
        {isError && (
          <p className="text-center text-red-500 mt-4">
            {isFetchBaseQueryError(error) && isApiError(error.data)
              ? error.data.message
              : ""}
          </p>
        )}
        <p className="text-center text-gray-600 mt-4">
          Don't have an account?{" "}
          <Link to="/register" className="text-blue-400 hover:underline">
            Sign Up
          </Link>
        </p>
      </div>
    </div>
  );
};

export default AuthPage;
