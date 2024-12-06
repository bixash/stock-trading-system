"use client";
import * as z from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useAuth } from "../components/AuthProvider";
import { Input } from "@/components/ui/input";
import { Button } from "./ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { useNavigate } from "react-router-dom";


const formSchema = z.object({
  username: z.string().min(4),
  password: z.string().min(4),
});

type formSchema = z.infer<typeof formSchema>;

export function LoginForm() {
  const {handleLogin } = useAuth();
  const navigate = useNavigate();
 
  const form = useForm<formSchema>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      username: "",
      password: "",
    },
  });

  const onSubmit = async (loginData: formSchema) => {
    handleLogin(loginData);
    navigate("/app", {"replace": true});
  };

  return (

      <div>
        <Form {...form}>
          <form
            onSubmit={form.handleSubmit(onSubmit)}
            className="max-w-md w-full flex flex-col gap-4"
          >
            <FormField
              control={form.control}
              name="username"
              render={({ field }) => {
                return (
                  <FormItem className="inline-block text-left">
                    <FormLabel>Username</FormLabel>
                    <FormControl>
                      <Input
                        className=" bg-bg_1 border rounded border-bg_2"
                        placeholder="Username"
                        type="text"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                );
              }}
            />

            <FormField
              control={form.control}
              name="password"
              render={({ field }) => {
                return (
                  <FormItem className="inline-block text-left">
                    <FormLabel>Password</FormLabel>
                    <FormControl>
                      <Input
                        className=" bg-bg_1 border rounded border-bg_2"
                        placeholder="Password"
                        type="password"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                );
              }}
            />

            <div className="w-full flex justify-evenly">
              <Button
                disabled={form.formState.isSubmitting}
                type="submit"
                className="w-full text-s rounded bg-text_5 text-white hover:bg-text_5/90"
                
              >
        
                Login
              </Button>
              
            </div>
          </form>
        </Form>
      </div>

  );
}
