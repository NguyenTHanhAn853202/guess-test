import { Typography } from "@mui/material";
import { useProfile } from "./hooks/useProfile";
import { memo } from "react";

const ProfilePage = () => {
  const { data } = useProfile();
  
  return (
    <div className="flex justify-center items-center size-full">
      <div className="w-100">
        <Typography variant="h3" className="mt-2" align="center">
          Profile
        </Typography>
        <Typography variant="h6">Username: {data?.username}</Typography>
        <Typography variant="h6">turns: {data?.turns}</Typography>
        <Typography variant="h6">score: {data?.score}</Typography>
      </div>
    </div>
  );
};

const Profile = memo(ProfilePage);

export { Profile };
