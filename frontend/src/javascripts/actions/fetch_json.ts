export default function(url : string) {
  return fetch(url)
    .then(function(response) {
      return response.json()
    });
}
