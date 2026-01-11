import { Link, Outlet, useLocation, useNavigate } from "@tanstack/react-router";
import { SCREEN } from "../../constants";
import { memo, useCallback, useEffect } from "react";
import { useUser } from "../../app/stores/user";
import { usePostLogout } from "../../app/stores/api";

const LayoutComponent = () => {
  const { isLogin, setIsLogin } = useUser();
  const location = useLocation();
  const navigate = useNavigate();

  const { mutateAsync, isSuccess } = usePostLogout();

  useEffect(() => {
    if (!isLogin) {
      navigate({ to: SCREEN.SIGN_IN });
    }
  }, [isLogin, navigate]);

  if (isSuccess) {
    if (isLogin) {
      setIsLogin(false);
    }
  }

  const handleLogout = useCallback(async () => {
    await mutateAsync(undefined, {
      onSuccess: () => {
        localStorage.removeItem("isLogin");
      },
    });
  }, [mutateAsync]);

  return (
    <div className="relative">
      <div className="flex justify-center gap-10 fixed w-full h-12 items-center bg-gray-300">
        <Link
          className={`hover:underline hover:opacity-70 ${
            location.pathname === SCREEN.GUESS ? "underline" : ""
          }`}
          to={SCREEN.GUESS}
        >
          Guess
        </Link>
        <Link
          className={`hover:underline hover:opacity-70 ${
            location.pathname === SCREEN.LEADERBOARD ? "underline" : ""
          }`}
          to={SCREEN.LEADERBOARD}
        >
          Leaderboard
        </Link>
        <Link
          className={`hover:underline hover:opacity-70 ${
            location.pathname === SCREEN.ME ? "underline" : ""
          }`}
          to={SCREEN.ME}
        >
          User Information
        </Link>

        <button
          onClick={handleLogout}
          className={`hover:underline hover:opacity-70 cursor-pointer`}
        >
          Logout
        </button>
      </div>
      <div
        className="px-10 pt-12"
        style={{
          height: "calc(100vh - 48px)",
        }}
      >
        <Outlet />
      </div>
    </div>
  );
};

const Layout = memo(LayoutComponent);

export { Layout };
