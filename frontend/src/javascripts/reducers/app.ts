interface State {
  refreshtoken : string;
  token : string;
}
const initialState : State = { refreshtoken: null, token: null }
export default (state = initialState) => {
  return state;
}
