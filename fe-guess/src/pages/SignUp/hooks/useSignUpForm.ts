import { useForm } from "react-hook-form";
import { object, string, type TypeOf } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";

type SignUpFormValues = TypeOf<typeof schema>;

const schema = object({
  username: string()
    .nonempty("Username is required")
    .min(8, "Username must be at least 8 characters"),
  password: string()
    .nonempty("Password is required")
    .min(8, "Password must be at least 8 characters"),
  confirmPassword: string().nonempty("Confirm Password is required"),
}).superRefine(({ password, confirmPassword }, ctx) => {
  if (password !== confirmPassword) {
    ctx.addIssue({
      code: "custom",
      message: "Passwords do not match",
      path: ["confirmPassword"],
    });
  }
});

const useSignupForm = () =>
  useForm<SignUpFormValues>({
    defaultValues: {
      username: "",
      password: "",
      confirmPassword: "",
    },
    resolver: zodResolver(schema),
    mode: "all",
  });

  export {useSignupForm, type SignUpFormValues};