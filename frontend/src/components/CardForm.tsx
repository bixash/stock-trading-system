import { headerLogo } from "@/assets/images";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";

interface CardProps {
  title: string;
  description?: string;
  handleClick: ()=>void;
  content: any;
  footer?: any;
}

export function CardForm({ title, description, content, footer, handleClick }: CardProps) {

  return (
    <Card className="w-[380px] rounded shadow-md mt-10 mb-10 mx-auto border-none bg-white">
      <CardHeader>
        <img
          src={headerLogo}
          alt="logo"
          className="mb-8 w-[200px]  mx-auto"
        />
        <CardTitle className="text-center">{title}</CardTitle>
        
      </CardHeader>
      <CardContent className="grid gap-4">{content}</CardContent>
      <CardFooter className="mb-0 pb-0">{footer}</CardFooter>
      <CardDescription className="text-center pb-6 hover:text-text_5" onClick={handleClick}>{description}</CardDescription>
    </Card>
  );
}

// <Button className="w-full">
// <Check /> Mark all as read
// </Button>
