import { useGetProfile } from "../../../app/stores/api";

const useProfile = () => {
  const { data: { data } = {} } = useGetProfile();
  return { data: data?.data };
};

export { useProfile };
