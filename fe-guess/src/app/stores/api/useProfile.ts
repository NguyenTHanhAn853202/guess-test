import { useQuery } from "@tanstack/react-query";
import { getProfile } from "../../services";

export const useGetProfile = () => {
  const query = useQuery({
    queryFn: ({ signal }) => getProfile(signal),
    queryKey: ["me"],
  });

  return query;
};
