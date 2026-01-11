import { useQuery } from "@tanstack/react-query";
import { getLeaderboard } from "../../services";

export const useGetLeaderboard = () => {
  const query = useQuery({
    queryFn: ({ signal }) => getLeaderboard(signal),
    queryKey: ["leaderboard"],
  });

  return {
    ...query,
    data: query.data?.data,
  };
};
