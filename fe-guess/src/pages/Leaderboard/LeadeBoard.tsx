import { List, ListItem, ListItemText, Typography } from "@mui/material";
import { useGetLeaderboard } from "../../app/stores/api";
import { memo } from "react";

const LeaderboardPage = () => {
  const { data } = useGetLeaderboard();

  const response = data?.data as { value?: string; score?: number }[];

  return (
    <div className="flex flex-col size-full items-center pt-10">
      <Typography variant="h3" align="center">
        Leaderboard
      </Typography>

      <List>
        {!response?.length ? (
          <p>No data</p>
        ) : (
          response?.map((item) => (
            <ListItem>
              <ListItemText
                primary={`Username: ${item.value}`}
                secondary={`Score: ${item.score}`}
              />
            </ListItem>
          ))
        )}
      </List>
    </div>
  );
};

const Leaderboard = memo(LeaderboardPage);

export { Leaderboard };
