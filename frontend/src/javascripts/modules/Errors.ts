class Errors {
  static handle(setErrors: any) {
    return (data: any) => {
      setErrors(data.errors.map((e: any) => ({ field: e.field, error: e.type })));
    }
  }
}

export default Errors;
