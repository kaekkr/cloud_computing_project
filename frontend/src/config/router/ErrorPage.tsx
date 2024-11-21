import { useRouteError, isRouteErrorResponse } from "react-router-dom";

const ErrorPage = () => {
  const error = useRouteError();

  let errorMessage = "An unknown error occurred.";
  let errorTitle = "Oops!";

  if (isRouteErrorResponse(error)) {
    if (error.status === 404) {
      errorTitle = "404 - Page Not Found";
      errorMessage = "The page you are looking for does not exist.";
    } else {
      errorMessage = error.statusText || error.data.message;
    }
  }

  return (
    <div className="min-h-screen flex flex-col justify-center items-center bg-gray-100">
      <h1 className="text-5xl font-bold text-red-600 mb-4">{errorTitle}</h1>
      <p className="text-lg text-gray-700 mb-2">
        Sorry, an error has occurred.
      </p>
      <p className="text-sm text-gray-500">{errorMessage}</p>
      <button
        className="mt-6 bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded"
        onClick={() => (window.location.href = "/")}
      >
        Go back to Home
      </button>
    </div>
  );
};

export default ErrorPage;
