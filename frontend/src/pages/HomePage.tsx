import React from "react";
import backgroundImage from "@/assets/banner.jpg";
import { Link } from "react-router-dom";

const HomePage: React.FC = () => {
  return (
    <div
      className="relative min-h-screen flex items-center justify-center bg-cover bg-center text-white"
      style={{
        backgroundImage: `url(${backgroundImage})`,
      }}
    >
      {/* Dark overlay */}
      <div className="absolute inset-0 bg-black bg-opacity-60"></div>

      {/* Centered Content */}
      <div className="relative text-center max-w-3xl p-6 bg-white bg-opacity-10 rounded-xl shadow-lg backdrop-blur-md">
        <h1 className="text-5xl font-extrabold mb-6">
          Welcome to your <br></br>
          <span className="text-blue-400">Virtual Healthcare Assistant</span>
        </h1>
        <p className="text-lg mb-8">
          Your trusted virtual healthcare assistant, offering personalized care
          at your fingertips.
        </p>
        <div className="flex flex-col sm:flex-row justify-center items-center space-y-4 sm:space-y-0 sm:space-x-4">
          <Link to={"dashboard"}>
            <button className="px-8 py-3 bg-blue-500 text-white rounded-full text-lg font-semibold hover:bg-blue-400">
              Get Started
            </button>
          </Link>
          <button className="px-8 py-3 bg-gray-200 text-gray-800 rounded-full text-lg font-semibold hover:bg-gray-300">
            Learn More
          </button>
        </div>
      </div>
    </div>
  );
};

export default HomePage;
