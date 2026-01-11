import {
  memo,
  useCallback,
  useMemo,
  type ChangeEvent,
  type FocusEvent,
} from "react";
import TextField, { type TextFieldProps } from "@mui/material/TextField";
import {
  type Control,
  type FieldValues,
  type Path,
  useController,
} from "react-hook-form";

type InputProps<T extends FieldValues> = TextFieldProps & {
  control: Control<T>;
  name: Path<T>;
  errorMessage?: string;
  isNotMessage?: boolean;
};

const InputComponent = <T extends FieldValues>({
  className,
  control,
  name,
  isNotMessage,
  errorMessage,
  type,
  onChange,
  onBlur,
  ...props
}: InputProps<T>) => {
  const {
    field,
    fieldState: { error },
  } = useController<T>({
    name,
    control,
  });

  const errorText = useMemo(() => {
    return isNotMessage && errorMessage
      ? undefined
      : errorMessage || error?.message;
  }, [isNotMessage, errorMessage, error?.message]);

  const handleOnBlur = useCallback(
    (e: FocusEvent<HTMLInputElement>) => {
      field.onBlur();
      onBlur?.(e);
    },
    [field, onBlur]
  );

  const handleOnChange = useCallback(
    (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
      if (type === "number") {
        field.onChange(Number(e.target.value ?? 0));
      } else field.onChange(e.target.value);

      onChange?.(e);
    },
    [field, onChange, type]
  );

  return (
    <div className={className}>
      <TextField
        {...field}
        onBlur={handleOnBlur}
        onChange={handleOnChange}
        size="small"
        className="size-full"
        error={!!errorText || !!errorMessage}
        helperText={errorText}
        type={type}
        {...props}
      />
    </div>
  );
};

const Input = memo(InputComponent) as typeof InputComponent;

export { Input };
