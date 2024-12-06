import { Button } from "@/components/ui/button";

import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";

import { toast } from "sonner";

import api from "@/api/auth";

interface DialogProps {
  orderId: number;
  ticker: string;
  open: boolean;
  setOpen: (open: boolean) => void;
}

export function DeleteOrderDialog({
  orderId,
  ticker,
  open,
  setOpen,
}: DialogProps) {
  async function handleDelete(orderId: number) {
    try {
      const response = await api.delete(`/orders?orderId=${orderId}`);
      toast.success(response.data.msg);
      setOpen(false);
    } catch (error) {}
  }

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogContent className="w-[400px] top-56 border-2 border-bg-1">
        <DialogHeader className="flex flex-row justify-between py-4 px-6 border-b-2 border-b-bg_1 bg-text_10 text-white">
          <DialogTitle className="text-[21px]">Exit the order?</DialogTitle>
        </DialogHeader>
        <DialogDescription className="text-[14px] text-bg_3 my-4 px-4 flex flex-col">
          <span className="font-semibold ">{ticker}</span>
          <span>#1{orderId}2024113</span>
        </DialogDescription>
        <DialogFooter className="py-3 px-6  border-t-2 border-t-bg_1">
          <div className="w-[240px] flex gap-6 ml-auto">
            <Button
              type="submit"
              onClick={() => handleDelete(orderId)}
              className="rounded border py-3 px-5 text-white hover:bg-white bg-text_10 text-base hover:border-text_10 hover:text-text_10 "
            >
              <span>Exit order</span>
            </Button>
            <DialogClose asChild>
              <Button
                className="rounded text-base bg-bg_2 text-white hover:border-bg_2 hover:text-bg_2"
                type="button"
                variant="outline"
              >
                Close
              </Button>
            </DialogClose>
          </div>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}
