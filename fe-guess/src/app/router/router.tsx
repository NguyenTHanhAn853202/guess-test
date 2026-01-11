import {
  createRootRoute,
  createRoute,
  createRouter,
} from "@tanstack/react-router";
import { SCREEN } from "../../constants/screen";
import { lazy } from "react";
import { Layout } from "../../components/layout/Layout";
import { AuthLayout } from "../../components/layout";

const SignUp = lazy(() => import("../../pages/SignUp"));
const SignIn = lazy(() => import("../../pages/SignIn"));
const Guess = lazy(() => import("../../pages/Guess"));
const Profile = lazy(() => import("../../pages/Profile"));
const Leaderboard = lazy(() => import("../../pages/Leaderboard"));

const rootRoute = createRootRoute();

const layoutRoute = createRoute({
  getParentRoute: () => rootRoute,
  component: Layout,
  id: "layout",
});

const authLayout = createRoute({
  getParentRoute: () => rootRoute,
  component: AuthLayout,
  id: "auth-layout",
});

const signUpPage = createRoute({
  getParentRoute: () => authLayout,
  path: SCREEN.SIGN_UP,
  component: SignUp,
});

const signInPage = createRoute({
  getParentRoute: () => authLayout,
  path: SCREEN.SIGN_IN,
  component: SignIn,
});

const GuessPage = createRoute({
  getParentRoute: () => layoutRoute,
  path: SCREEN.GUESS,
  component: Guess,
});

const ProfilePage = createRoute({
  getParentRoute: () => layoutRoute,
  path: SCREEN.ME,
  component: Profile,
});

const LeaderboardPage = createRoute({
  getParentRoute: () => layoutRoute,
  path: SCREEN.LEADERBOARD,
  component: Leaderboard,
});

const routeTree = rootRoute.addChildren([
  authLayout.addChildren([signUpPage, signInPage]),
  layoutRoute.addChildren([GuessPage, ProfilePage,LeaderboardPage]),
]);

const router = createRouter({ routeTree });

export { router };
