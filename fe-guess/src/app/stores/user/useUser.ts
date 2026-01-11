import { create } from "zustand";

interface UserState {
  isLogin: boolean;
}

interface ActionState {
  setIsLogin: (value: boolean) => void;
}

const initialState = {
  isLogin: false,
};

export const useUser = create<UserState & ActionState>((set) => ({
  ...initialState,
  setIsLogin: (value) => set({ isLogin: value }),
}));

