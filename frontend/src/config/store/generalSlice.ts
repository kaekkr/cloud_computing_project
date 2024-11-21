import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { apiSlice } from "../api/apiSlice";

interface GeneralState {
  sidebarClosed: boolean;
  pageTitle: string;
  hasSubscription: boolean;
}

const initialState: GeneralState = {
  sidebarClosed: false,
  pageTitle: "",
  hasSubscription: false,
};

const generalSlice = createSlice({
  name: "general",
  initialState,
  reducers: {
    toggleSidebar: (state) => {
      state.sidebarClosed = !state.sidebarClosed;
    },
    setSidebarClosed: (state, action: PayloadAction<boolean>) => {
      state.sidebarClosed = action.payload;
    },
    setPageTitle: (state, action) => {
      state.pageTitle = action.payload;
    },
    setHasSubscription: (state, action: PayloadAction<boolean>) => {
      localStorage.setItem("hasSubscription", "1");
      state.hasSubscription = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder.addMatcher(apiSlice.endpoints.subscribe.matchFulfilled, (state) => {
      state.hasSubscription = true;
    });
  },
});

export const {
  toggleSidebar,
  setSidebarClosed,
  setPageTitle,
  setHasSubscription,
} = generalSlice.actions;
export default generalSlice.reducer;
