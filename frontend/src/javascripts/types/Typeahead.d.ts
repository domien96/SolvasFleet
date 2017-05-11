declare module 'react-bootstrap-typeahead' {

  interface TypeaheadProps {
    align?: 'justify' | 'left' | 'right';
    allowNew?: boolean;
    defaultSelected?: any[];
    disabled?: boolean;
    emptyLabel?: string;
    labelKey?: string;
    maxHeight?: number;
    minLength?: number;
    multiple?: boolean;
    name?: string;
    newSelectionPrefix?: string;
    onBlur?: (e: Event) => any;
    onChange?: (selected: any[]) => any;
    onInputChange?: (input: string) => any;
    options: any[];
    paginateResults?: number;
    paginationText?: string;
    placeholder?: string;
    renderMenuItemChildren?: (props: TypeaheadProps, option: any, index: number) => any;
    selected?: any[];
  }

  export const Typeahead: React.ClassicComponentClass<TypeaheadProps>;

  export default Typeahead;
}
