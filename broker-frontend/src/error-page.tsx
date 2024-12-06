import {
  useRouteError,
  isRouteErrorResponse,
  useNavigate,
} from "react-router-dom";

export default function ErrorPage() {
  const navigate = useNavigate();
  const error = useRouteError() as Error;

  if (!isRouteErrorResponse(error)) {
    return null;
  }

  return (
    <>
      <div id="error-page">
        <h1>Oops! Something went wrong 😢</h1>
        <p>Sorry, an unexpected error has occurred.</p>
        <p>{error.data}</p>
        <p>
          <i>{error.statusText || error.message}</i>
        </p>
        <button onClick={() => navigate(-1)}>&larr; Go back</button>
      </div>
    </>
  );
}
