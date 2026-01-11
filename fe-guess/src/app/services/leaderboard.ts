import { baseApi } from "../../configuration/api";
import { ApiPath } from "../../constants";

export const getLeaderboard = (signal?: AbortSignal) => {
  return baseApi.get(ApiPath.LEADERBOARD, { signal: signal });
};
