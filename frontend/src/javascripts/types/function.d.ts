namespace SFunction {
  export type Field =
    'id' | 'company' | 'role' | 'user';
}

interface SFunctionData {
  id?: number;
  company?: number;
  role?: number;
  user?: number;
}

namespace SFunctions {
  export interface Data {
    [ Sfunctions: string ]: any;
  }
}

namespace Choice {
  export interface Data {
    key: string;
    label: string;
  }
}
