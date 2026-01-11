import { Button, Typography } from "@mui/material";
import { memo } from "react";
import { Input } from "../../components/Input/Input";
import { useGuess } from "./hooks/useGuess";

const GuessPage = () => {
  const { control, handleSubmitForm, isSubmitting, response,isValid } = useGuess();
  return (
    <div className="flex justify-center pt-50  h-full">
      <div className="w-100 gap-4 flex flex-col">
        <Typography variant="h3" align="center">
          Guess
        </Typography>
        <form onSubmit={handleSubmitForm} className="flex flex-col gap-3">
          <Input control={control} name="number" label="Number" type="number" />

          <Button type="submit" variant="contained" disabled={isSubmitting || !isValid}>
            Submit
          </Button>
        </form>

        {response && (
          <div className="flex flex-col items-center">
            <Typography
              variant="h6"
              color={response.correct ? "success" : "error"}
            >
              {response.correct ? "You win" : "You lose"}
            </Typography>

            <Typography variant="h6">
              The correct number: {response.correctNumber}
            </Typography>

            <Typography variant="h6">
              Your current turns: {response.turns}
            </Typography>

            <Typography variant="h6">
              Your current score: {response.score}
            </Typography>
          </div>
        )}
      </div>
    </div>
  );
};

const Guess = memo(GuessPage);

export { Guess };
