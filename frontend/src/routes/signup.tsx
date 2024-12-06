import { CardForm } from "@/components/CardForm";
import { SignupForm } from "@/components/SignupForm";
import { useNavigate } from "react-router-dom";


function Signup() {
  const navigate = useNavigate();
  return (
    <CardForm
      title="Signup Now!"
      description="Already have an account? Login!"
      content={<SignupForm />} handleClick={()=> navigate("/login")}
     
    />
  );
}

export default Signup;
