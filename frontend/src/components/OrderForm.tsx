import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
// import { Label } from "@/components/ui/label";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
// import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";

const schema = z
  .object({
    ticker: z.string(),
    productType: z.string(),
    quantity: z.number(),
    price: z.number(),
    triggerPrice: z.number(),
    validity: z.string(),
    transactionType: z.string(),
    orderType: z.string(),

    clientId: z.coerce.number({
      required_error: "Age is required",
      invalid_type_error: "id be a number",
    }),
  })
  .required();

type formSchema = z.infer<typeof schema>;

function OrderForm() {
  const form = useForm<formSchema>({
    resolver: zodResolver(schema),
    defaultValues: {
      clientId: 12,
    },
  });

  const onSubmit = async (data: formSchema) => {
    try {
      await new Promise((resolve) => setTimeout(resolve, 2000));
      throw new Error();
    } catch (error) {}

    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    };

    await fetch("http://localhost:8082/broker/orders", requestOptions);
    // const jsonData = await response.json();

    // console.log(jsonData);
  };

  return (
    <div className="p-6">
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <FormField
            control={form.control}
            name="productType"
            render={({ field }) => (
              <FormItem className="space-y-3">
                <FormControl>
                  <RadioGroup
                    onValueChange={field.onChange}
                    // defaultValue={field.value}
                    defaultValue="cnc"
                    className="flex flex-col space-y-1"
                  >
                    <FormItem className="flex items-center space-x-3 space-y-0">
                      <FormControl>
                        <RadioGroupItem value="cnc" />
                      </FormControl>
                      <FormLabel className="font-normal">
                        Longterm <span>CNC</span>
                      </FormLabel>
                    </FormItem>
                  </RadioGroup>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <div className="flex flex-row justify-between">
            <FormField
              control={form.control}
              name="quantity"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Qty.</FormLabel>
                  <FormControl>
                    <Input placeholder="" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="price"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Price</FormLabel>
                  <FormControl>
                    <Input placeholder="" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="triggerPrice"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Trigger Price</FormLabel>
                  <FormControl>
                    <Input placeholder="" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>

          <FormField
            control={form.control}
            name="orderType"
            render={({ field }) => (
              <FormItem className="space-y-3">
                <FormControl>
                  <RadioGroup
                    onValueChange={field.onChange}
                    defaultValue="limit"
                    className="flex flex-col space-y-1"
                  >
                    <FormItem className="flex items-center space-x-3 space-y-0">
                      <FormControl>
                        <RadioGroupItem value="market" />
                      </FormControl>
                      <FormLabel className="font-normal">
                        Market
                      </FormLabel>
                    </FormItem>
                    <FormItem className="flex items-center space-x-3 space-y-0">
                      <FormControl>
                        <RadioGroupItem value="limit" />
                      </FormControl>
                      <FormLabel className="font-normal">
                        Limit
                      </FormLabel>
                    </FormItem>
                  </RadioGroup>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="validity"
            render={({ field }) => (
              <FormItem className="space-y-3">
                <FormLabel>Validity</FormLabel>
                <FormControl>
                  <RadioGroup
                    onValueChange={field.onChange}
                    defaultValue="day"
                    className="flex flex-col space-y-1"
                  >
                    <FormItem className="flex items-center space-x-3 space-y-0">
                      <FormControl>
                        <RadioGroupItem value="day" />
                      </FormControl>
                      <FormLabel className="font-normal">
                        Day
                      </FormLabel>
                    </FormItem>
                    <FormItem className="flex items-center space-x-3 space-y-0">
                      <FormControl>
                        <RadioGroupItem value="minutes" />
                      </FormControl>
                      <FormLabel className="font-normal">
                        Minutes
                      </FormLabel>
                    </FormItem>
                    <FormItem className="flex items-center space-x-3 space-y-0">
                      <FormControl>
                        <RadioGroupItem value="immediate" />
                      </FormControl>
                      <FormLabel className="font-normal">
                        Immediate
                      </FormLabel>
                    </FormItem>
                   
                  </RadioGroup>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          {/* <Button type="submit">Submit</Button> */}
        </form>
      </Form>
    </div>
  );
}

export default OrderForm;
