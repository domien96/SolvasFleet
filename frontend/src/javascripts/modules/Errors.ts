class Errors {
  static handle(setErrors: any) {
    return (data: any) => {
    	console.log(data);
      setErrors(data.errors.map((e: any) => ({ field: e.field, type: e.type })));
    }
  }
}

export default Errors;
