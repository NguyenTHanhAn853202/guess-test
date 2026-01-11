import type { FieldValues, Path, UseFormSetError } from "react-hook-form";
import type { ErrorList } from "../types";

export const errorMapper = <T extends FieldValues>(
  errors: ErrorList[],
  setError: UseFormSetError<T>
) => {
  if (!errors) return;

  errors.forEach(({ errorMessage, fieldName }) => {
    setError(fieldName as Path<T>, { message: errorMessage });
  });
};
