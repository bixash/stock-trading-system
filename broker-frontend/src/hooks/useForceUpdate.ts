import { useState } from "react";

export default function useForceUpdate() {
  const [, setTick] = useState(0);
  const update = () => setTick((tick) => tick + 1);
  return update;
}
