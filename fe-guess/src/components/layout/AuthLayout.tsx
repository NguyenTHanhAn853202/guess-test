import { Outlet, useNavigate } from "@tanstack/react-router";
import { SCREEN } from "../../constants";
import { memo } from "react";
import { useUser } from "../../app/stores/user";

const AuthLayoutComponent = () => {
  const { isLogin } = useUser();
  const navigate = useNavigate();

  if (isLogin) {
    navigate({ to: SCREEN.GUESS });
  }

  return (
    <>
      <Outlet />
    </>
  );
};

const AuthLayout = memo(AuthLayoutComponent);

export { AuthLayout };
