import { RouterProvider } from "@tanstack/react-router";
import { router } from "./app/router";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { useEffect } from "react";
import { useUser } from "./app/stores/user";

const queryClient = new QueryClient();

function App() {
  const { setIsLogin } = useUser();

  useEffect(() => {
    if (localStorage.getItem("isLogin") === "true") {
      setIsLogin(true);
    }
  }, [setIsLogin]);

  return (
    <QueryClientProvider client={queryClient}>
      <RouterProvider router={router} />
    </QueryClientProvider>
  );
}

export default App;
