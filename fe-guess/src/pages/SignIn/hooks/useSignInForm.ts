import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { object, string, type TypeOf } from "zod";


type SignUpFormValues = TypeOf<typeof schema>;

const schema = object({
    username: string().nonempty("Username is required").min(8, "Username must be at least 8 characters"),
    password: string().nonempty("Password is required").min(8, "Password must be at least 8 characters"),
})

const useSignInForm = () => useForm<SignUpFormValues>({
    defaultValues: {
        username: "",
        password: "",
    },
    resolver: zodResolver(schema),
    mode: "all",
});

export {useSignInForm, type SignUpFormValues};