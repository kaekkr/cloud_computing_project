import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface AuthState {
  username: string | null;
  token: string | null;
  isAuthenticated: boolean;
}

const initialState: AuthState = {
  username: null,
  token: null,
  isAuthenticated: false,
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    login: (
      state,
      action: PayloadAction<{ username: string; token: string }>
    ) => {
      const rawToken = action.payload.token;
      const trimmedToken = rawToken.startsWith("Bearer ")
        ? rawToken.substring(7)
        : rawToken;
      state.username = action.payload.username;
      state.token = trimmedToken;
      state.isAuthenticated = true;

      localStorage.setItem("username", action.payload.username);
      localStorage.setItem("token", trimmedToken);
    },
    logout: (state) => {
      state.token = null;
      state.username = null;
      state.isAuthenticated = false;

      localStorage.removeItem("username");
      localStorage.removeItem("token");
    },
  },
});

export const { login, logout } = authSlice.actions;
export default authSlice.reducer;
