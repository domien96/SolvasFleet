interface PaginationQuery {
  limit : number,
  page : number
}

interface PaginationResponse {
  total : number,
  first : string,
  last : string,
  limit : number,
  offset : number,
  previous : string,
  next : string
}
