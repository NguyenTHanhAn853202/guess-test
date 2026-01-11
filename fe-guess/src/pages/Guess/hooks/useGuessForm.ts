import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { number, object, type TypeOf } from "zod";

type GuessForm = TypeOf<typeof schema>;

const schema = object({
  number: number()
    .min(1, "The min value is from 1 to 5")
    .max(5, "The min value is from 1 to 5")
    .nonoptional("The field is required"),
});

const useGuessForm = () =>
  useForm<GuessForm>({
    defaultValues: {
      number: 0,
    },
    mode: "all",
    resolver: zodResolver(schema),
  });

export { useGuessForm, type GuessForm };
