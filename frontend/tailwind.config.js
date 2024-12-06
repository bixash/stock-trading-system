/** @type {import('tailwindcss').Config} */
export default {
  darkMode: ["class"],
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      fontFamily: {
        opensans: ["Open Sans", "sans-serif"],
      },
      fontSize: {
        s: "13px",
        sm: "12px",
        xs: "10px",
        xxs: "7px",
      },

      flex: {
        flex: "0 0 var(--left-content-width)",
      },
      height: {
        "--sidebar-marketwatch": "var(--sidebar-height)",
        "--sidebar-content": "var(--sidebar-content)",
      },

      width: {
        "--wrapper-width": "var(--wrapper-width)",
        "--left-content-width": "var(--left-content-width)",
        "--right-content-width": "var(--right-content-width)",
      },
      borderRadius: {
        lg: "var(--radius)",
        md: "calc(var(--radius) - 2px)",
        sm: "calc(var(--radius) - 4px)",
      },
      colors: {
        bcd: "var(--color-tundora-grey)",
        background: "var(--color-bg-default)",
        foreground: "hsl(var(--foreground))",
        bg_0: "var(--color-bg-0)",
        bg_1: "var(--color-bg-1)",
        bg_2: "var(--color-bg-2)",
        bg_3: "var(--color-bg-3)",
        bg_4: "var(--color-bg-4)",
        bg_5: "var(--color-bg-5)",
        bg_10: "var(--color-bg-10)",
        text_4: "var(--color-text-4)",
        text_5: "var(--color-text-5)",
        text_8: "var(--color-text-8)",
        text_10: "var(--color-text-10)",
        card: {
          DEFAULT: "hsl(var(--card))",
          foreground: "hsl(var(--card-foreground))",
        },
        popover: {
          DEFAULT: "hsl(var(--popover))",
          foreground: "hsl(var(--popover-foreground))",
        },
        primary: {
          DEFAULT: "hsl(var(--color-bg-default))",
          foreground: "hsl(var(--primary-foreground))",
        },
        secondary: {
          DEFAULT: "hsl(var(--secondary))",
          foreground: "hsl(var(--secondary-foreground))",
        },
        muted: {
          DEFAULT: "hsl(var(--muted))",
          foreground: "hsl(var(--muted-foreground))",
        },
        accent: {
          DEFAULT: "hsl(var(--accent))",
          foreground: "hsl(var(--accent-foreground))",
        },
        destructive: {
          DEFAULT: "hsl(var(--destructive))",
          foreground: "hsl(var(--destructive-foreground))",
        },
        border: "hsl(var(--border))",
        input: "hsl(var(--input))",
        ring: "hsl(var(--ring))",
        chart: {
          1: "hsl(var(--chart-1))",
          2: "hsl(var(--chart-2))",
          3: "hsl(var(--chart-3))",
          4: "hsl(var(--chart-4))",
          5: "hsl(var(--chart-5))",
        },
        sidebar: {
          DEFAULT: "hsl(var(--sidebar-background))",
          foreground: "hsl(var(--sidebar-foreground))",
          primary: "hsl(var(--sidebar-primary))",
          "primary-foreground": "hsl(var(--sidebar-primary-foreground))",
          accent: "hsl(var(--sidebar-accent))",
          "accent-foreground": "hsl(var(--sidebar-accent-foreground))",
          border: "hsl(var(--sidebar-border))",
          ring: "hsl(var(--sidebar-ring))",
        },
      },
    },
  },
  plugins: [require("tailwindcss-animate")],
};
