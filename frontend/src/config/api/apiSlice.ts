import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { RootState } from "../store/store";

type RegisterRequest = {
  id?: number;
  username: string;
  password: string;
  role?: string;
  hasSubscription?: boolean;
};

export const apiSlice = createApi({
  reducerPath: "api",
  baseQuery: fetchBaseQuery({
    // baseUrl: "http://localhost:8080/api/v1",
    baseUrl:
      "https://cloudcomputingproject-gchjgaedfzdmf4bd.switzerlandnorth-01.azurewebsites.net/api/v1",

    prepareHeaders: (headers, { getState }) => {
      const token = (getState() as RootState).auth?.token;
      if (token) {
        headers.set("Authorization", `Bearer ${token}`);
      }
      return headers;
    },
  }),
  endpoints: (builder) => ({
    registerUser: builder.mutation<string, RegisterRequest>({
      query: (userData) => ({
        url: "/auth/register",
        method: "POST",
        body: userData,
        responseHandler: (response) => response.text(),
      }),
    }),
    loginUser: builder.mutation<
      { token: string; username: string },
      RegisterRequest
    >({
      query: (userData) => ({
        url: "/auth/login",
        method: "POST",
        body: userData,
      }),
    }),
    getLoginSuccess: builder.query<string, void>({
      query: () => "/auth/success",
    }),

    getLoginFailure: builder.query<string, void>({
      query: () => "/auth/failure",
    }),
    sendMessage: builder.mutation<
      {
        response: string;
        status: string;
      }, // Response type
      { userInput: string } // Request body type
    >({
      query: (body) => ({
        url: "bot/message",
        method: "POST",
        body,
      }),
    }),

    subscribe: builder.mutation<
      { message: string; username: string },
      { username: string }
    >({
      query: ({ username }) => {
        console.log("subscribe ", username);
        return {
          url: "/subscription/subscribe",
          method: "POST",
          params: { username },
        };
      },
    }),
    unsubscribe: builder.mutation<
      { message: string; username: string },
      { username: string }
    >({
      query: ({ username }) => ({
        url: "/subscription/unsubscribe",
        method: "POST",
        params: { username },
      }),
    }),
  }),
});

export const {
  useRegisterUserMutation,
  useLoginUserMutation,
  useSendMessageMutation,
  useSubscribeMutation,
} = apiSlice;
