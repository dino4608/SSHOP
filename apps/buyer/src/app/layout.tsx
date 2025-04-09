import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import Header from "@/components/layout/Header";
import Footer from "@/components/layout/Footer";

// Set up font variables
const inter = Inter({
  variable: "--font-inter-sans",
  subsets: ["latin"]
});

// Set up metadata for the page
export const metadata: Metadata = {
  title: "DEAL | Spend Less, Shop More.",
  description: "The best shopping experience.",
  icons: {
    icon: "/favicon.ico",
    shortcut: "/favicon.ico",
  }
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className={`${inter.className} antialiased bg-white text-gray-900 dark:bg-gray-900 dark:text-gray-100`}>
        <Header />

        {children}

        <Footer />
      </body>
    </html>
  );
}
