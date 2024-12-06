import { Button } from "@/components/ui/button";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Input } from "@/components/ui/input";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { z } from "zod";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";

("use client");

import { toast } from "sonner";

import { ReactNode, useEffect, useState } from "react";
import { Separator } from "@/components/ui/separator";
import { useAuth } from "./AuthProvider";
import { UseStockInfo } from "./StockInfoProvider";
import api from "@/api/auth";
import { useParams } from "react-router-dom";
import { getExpiryDate } from "@/utils";

interface GttDialogProps {
  tickerProps: string;
  children: ReactNode;
}

const schema = z.object({
  ticker: z.string(),
  clientId: z.string(),
  productType: z.string().default("cnc"),
  transactionType: z.string(),
  price: z.coerce.number().min(1),
  orderQuantity: z.coerce.number().min(1),
  orderType: z.string().default("limit"),
  triggerPrice: z.coerce.number().min(1),
  triggerType: z.string().default("single"),
});

type formSchema = z.infer<typeof schema>;

export function GttOrderDialog({ tickerProps, children }: GttDialogProps) {
  const params = useParams<{ ticker: string }>();
  const [open, setOpen] = useState(false);
  const { currentUser } = useAuth();
  const { stockInfoList } = UseStockInfo();
  const [ltp, setLtp] = useState<number>(0);
  const expiryDate = getExpiryDate();

  const form = useForm<formSchema>({
    resolver: zodResolver(schema),
    defaultValues: {
      ticker: params.ticker,
      transactionType: "buy",
      triggerType: "single",
      triggerPrice: ltp + ltp * 0.05,
      price: ltp + ltp * 0.05,
      orderQuantity: 1,
      orderType: "limit",
      productType: "cnc",
      clientId: currentUser!,
    },
  });
  useEffect(() => {
    if (stockInfoList) {
      for (let stock of stockInfoList) {
        if (stock.ticker == tickerProps) {
          setLtp(stock.lastTradedPrice);
        }
      }
    }
  }, [stockInfoList, tickerProps]);

  const triggerPrice = form.watch("triggerPrice");
  const triggerPercent = ((triggerPrice - ltp) / ltp) * 100;

  useEffect(() => {
    form.reset({
      ticker: tickerProps,
      orderQuantity: 1,
      transactionType: "buy",
      triggerPrice: ltp + ltp * 0.05,
      price: ltp + ltp * 0.05,
      orderType: "limit",
      productType: "cnc",
      clientId: currentUser!,
    });
  }, [tickerProps, ltp, currentUser, form]);

  function onError(errors: any) {
    console.log(errors);
  }
  const onSubmit = async (orderData: formSchema) => {
    try {
      console.log(orderData);

      const response = await api.post("/gtt-orders", orderData);

      if (response.data) {
        if (response.data.success) {
          toast.success(response.data.msg);
        } else {
          toast.error(response.data.msg);
        }

        setOpen(false);
      }
    } catch (error) {}
  };

  useEffect(() => {
    if (form.formState.isSubmitSuccessful) {
      form.reset();
    }
  }, [form]);

  const transaction = form.watch("transactionType").match("sell");

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit, onError)}
          className="space-y-8"
        >
          <DialogTrigger asChild>{children}</DialogTrigger>
          <DialogContent className="w-[600px] outline-none">
            <DialogHeader
              className={`flex flex-row justify-between text-white p-4 ${
                transaction ? "bg-text_5" : "bg-text_4"
              }`}
            >
              <div>
                <DialogTitle className="text-[15px]">
                  {params.ticker}
                </DialogTitle>
                <DialogDescription className="text-[12px]">
                  <span>Rs. {ltp}</span>
                </DialogDescription>
              </div>
            </DialogHeader>

            <div className="flex flex-col justify-evenly -mt-4">
              <div className="flex justify-between w-full px-6 py-7 bg-bg_1 ">
                <div className="flex flex-col w-[400px]">
                  <span className="inline-block mb-2 text-bg_3">
                    Transaction type
                  </span>

                  <FormField
                    control={form.control}
                    name="transactionType"
                    render={({ field }) => (
                      <FormItem className="space-y-3 w-fit my-2">
                        <FormControl>
                          <RadioGroup
                            onValueChange={field.onChange}
                            defaultValue="buy"
                            className="flex flex-row justify-between w-[130px]"
                          >
                            <FormItem className="flex items-center space-x-2 space-y-0">
                              <FormControl>
                                <RadioGroupItem
                                  className={
                                    transaction ? "text-text_5" : "text-text_4"
                                  }
                                  value="buy"
                                />
                              </FormControl>
                              <FormLabel className="font-normal">Buy</FormLabel>
                            </FormItem>
                            <FormItem className="flex items-center space-x-2 space-y-0">
                              <FormControl>
                                <RadioGroupItem
                                  className={
                                    transaction ? "text-text_5" : "text-text_4"
                                  }
                                  value="sell"
                                />
                              </FormControl>
                              <FormLabel className="font-normal">
                                Sell
                              </FormLabel>
                            </FormItem>
                          </RadioGroup>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>
                <div className="flex flex-col">
                  <span className="inline-block mb-2 text-bg_3">
                    Trigger type
                  </span>
                  <FormField
                    control={form.control}
                    name="triggerType"
                    render={({ field }) => (
                      <FormItem className="space-y-3 w-fit my-2">
                        <FormControl>
                          <RadioGroup
                            onValueChange={field.onChange}
                            defaultValue="single"
                            className="flex flex-row justify-between w-[130px]"
                          >
                            <FormItem className="flex items-center space-x-2 space-y-0">
                              <FormControl>
                                <RadioGroupItem
                                  className={
                                    transaction ? "text-text_5" : "text-text_4"
                                  }
                                  value="single"
                                />
                              </FormControl>
                              <FormLabel className="font-normal">
                                Single
                              </FormLabel>
                            </FormItem>
                            <FormItem className="flex items-center space-x-2 space-y-0">
                              <FormControl>
                                <RadioGroupItem
                                  disabled
                                  className={
                                    transaction ? "text-text_5" : "text-text_4"
                                  }
                                  value="oco"
                                />
                              </FormControl>
                              <FormLabel className="font-normal">OCO</FormLabel>
                            </FormItem>
                          </RadioGroup>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <span className=" inline-block mt-2 text-[11px]  ">
                    The order is placed when the Last Traded Price (LTP) crosses
                    the trigger price. Can be used to enter or exit a position.
                  </span>
                </div>
              </div>

              <div className="flex w-[335px] gap-32 pt-3 ml-auto">
                <div>
                  <FormField
                    control={form.control}
                    name="productType"
                    render={({ field }) => (
                      <FormItem className="space-y-3">
                        <FormControl>
                          <RadioGroup
                            onValueChange={field.onChange}
                            defaultValue="cnc"
                            className="flex flex-col space-y-1 "
                          >
                            <FormItem className="flex items-center space-x-3 space-y-0">
                              <FormControl>
                                <RadioGroupItem
                                  className={
                                    transaction ? "text-text_5" : "text-text_4"
                                  }
                                  value="cnc"
                                />
                              </FormControl>
                              <FormLabel className="font-normal">CNC</FormLabel>
                            </FormItem>
                          </RadioGroup>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>
                <div>
                  {/* <FormField
                    control={form.control}
                    name="orderType"
                    render={({ field }) => (
                      <FormItem className="space-y-3 w-fit mx-auto">
                        <FormControl>
                          <RadioGroup
                            onValueChange={field.onChange}
                            defaultValue="limit"
                            className="flex flex-row justify-between w-[165px]"
                          >
                            <FormItem className="flex items-center space-x-2 space-y-0">
                              <FormControl>
                                <RadioGroupItem
                                  className={
                                    transaction ? "text-text_5" : "text-text_4"
                                  }
                                  value="limit"
                                />
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
                  /> */}
                  <FormField
                    control={form.control}
                    name="orderType"
                    render={({ field }) => (
                      <FormItem className="space-y-3 w-fit mx-auto">
                        <FormControl>
                          <RadioGroup
                            onValueChange={field.onChange}
                            defaultValue="limit"
                            className="flex flex-row justify-between w-[130px]"
                          >
                            <FormItem className="flex items-center space-x-2 space-y-0">
                              <FormControl>
                                <RadioGroupItem
                                  className={
                                    transaction ? "text-text_5" : "text-text_4"
                                  }
                                  value="limit"
                                />
                              </FormControl>
                              <FormLabel className="font-normal">
                                Limit
                              </FormLabel>
                            </FormItem>
                            <FormItem className="flex items-center space-x-2 space-y-0">
                              <FormControl>
                                <RadioGroupItem
                                  className={
                                    transaction ? "text-text_5" : "text-text_4"
                                  }
                                  value="market"
                                />
                              </FormControl>
                              <FormLabel className="font-normal">
                                Market
                              </FormLabel>
                            </FormItem>
                            
                          </RadioGroup>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>
              </div>

              <div className="flex flex-row justify-between mt-2 mb-4 px-6">
                <div>
                  <FormField
                    control={form.control}
                    name="triggerPrice"
                    render={({ field }) => (
                      <FormItem className="flex flex-col text-bg_3">
                        <FormLabel className="bg-white w-fit ml-[13px] z-[1] mb-[-15px] px-1">
                          Trigger Price
                        </FormLabel>
                        <FormControl>
                          <Input
                            type="number"
                            className="pt-3 rounded text-xl border-bg_2 w-[140px]"
                            {...field}
                            value={triggerPrice}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />

                  <span className="bg-white w-fit ml-2 h-fit p-0 text-sm">
                    <span>{triggerPercent.toFixed(2)}</span> % of LTP
                  </span>
                </div>
                <div className="flex flex-row ml-auto w-[310px] justify-between">
                  <FormField
                    control={form.control}
                    name="orderQuantity"
                    render={({ field }) => (
                      <FormItem className="flex flex-col text-bg_3">
                        <FormLabel className="bg-white w-fit ml-[13px] z-[1] mb-[-15px] px-1">
                          Qty.
                        </FormLabel>
                        <FormControl>
                          <Input
                            type="number"
                            className="pt-3 rounded  text-xl border-bg_2 w-[140px]"
                            {...field}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />

                  <FormField
                    control={form.control}
                    name="price"
                    render={({ field }) => (
                      <FormItem className="flex flex-col text-bg_3">
                        <FormLabel className="bg-white w-fit ml-[13px] z-[1] mb-[-15px] px-1">
                          Price
                        </FormLabel>
                        <FormControl>
                          <Input
                            type="number"
                            className="pt-3 rounded text-xl border-bg_2 w-[140px]"
                            {...field}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>
              </div>

              <Separator className="h-[0.5px] bg-[#efefef]" />
            </div>
            <DialogFooter className="-mt-4 py-5 px-6 bg-bg_1">
              <div>
                <span className=" inline-block text-[11px] w-[340px]">
                  By placing, I agree to the terms and accept that trigger
                  executions are not guaranteed. This trigger expires on{" "}
                  {expiryDate}.
                </span>
              </div>

              <div className="w-[140px] flex justify-between ">
                <Button
                  type="submit"
                  onClick={form.handleSubmit(onSubmit, onError)}
                  className={`rounded border w-[70px] py-4 px-6 text-white hover:bg-white ${
                    transaction
                      ? " bg-text_5 hover:border-text_5 hover:text-text_5 "
                      : " bg-text_4 hover:border-text_4 hover:text-text_4 "
                  }`}
                >
                  Place
                </Button>
                <DialogClose asChild>
                  <Button
                    onClick={() => {
                      form.reset();
                    }}
                    className="rounded bg-bg_2 text-white hover:border-bg_2 hover:text-bg_2"
                    type="button"
                    variant="outline"
                  >
                    Close
                  </Button>
                </DialogClose>
              </div>
            </DialogFooter>
          </DialogContent>
        </form>
      </Form>
    </Dialog>
  );
}
