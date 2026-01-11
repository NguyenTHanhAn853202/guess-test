import { Button, Typography } from "@mui/material";
import { Input } from "../../components/Input/Input";
import { useSignUp } from "./hooks/useSignUp";
import { Link } from "@tanstack/react-router";
import { SCREEN } from "../../constants/screen";
import { memo } from "react";

const SignUpPage = () => {
  const { control, isSubmitting, handleSubmitForm, isValid } = useSignUp();
  return (
    <div className="flex flex-col items-center justify-center min-h-screen">
      <div className="w-100 flex gap-4 flex-col">
        <Typography variant="h3" align="center">
          Sign up
        </Typography>
        <form onSubmit={handleSubmitForm} className="flex flex-col gap-4">
          <Input control={control} name="username" label="username" />
          <Input
            control={control}
            name="password"
            label="password"
            type="password"
          />
          <Input
            control={control}
            name="confirmPassword"
            label="confirm password"
            type="password"
          />
          <Button variant="contained"  type="submit" disabled={isSubmitting || !isValid}>
            Sign up
          </Button>
        </form>
        <Link className="text-blue-500 hover:underline text-center" to={SCREEN.SIGN_IN}>Sign in</Link>
      </div>
    </div>
  );
};

const SignUp = memo(SignUpPage);

export { SignUp };
