import { useAuth } from "@/components/AuthProvider";

const dashboard = () => {
  const { currentUser } = useAuth();
  return (
    <div className="p-6">
      <div className="text-2xl text-bg_3">Hi, {currentUser}</div>
    </div>
  );
};

export default dashboard;
