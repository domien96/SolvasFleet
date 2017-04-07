interface PaginationQuery {
  limit : number,
  page : number
}

interface PaginationResponse {
  total : number,
  first : number,
  last : number,
  limit : number,
  offset : number,
  previous : number,
  next : number
}
