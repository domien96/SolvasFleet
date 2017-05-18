import React from 'react';
import ReactPaginate from 'react-paginate';

interface Props {
  onClick: any;
  response: PaginationResponse;
}

class Pagination extends React.Component<Props, {}> {
  render() {
    const nop = Math.ceil(this.props.response.total / this.props.response.limit);
    const onPageChange = (r: any) => {
      this.props.onClick({ page: r.selected, limit: 20 })
    }

    if (this.props.response.total <= this.props.response.limit) {
      return null;
    }

    return (
      <ReactPaginate
        onPageChange={ onPageChange }
        pageCount={ nop }
        pageRangeDisplayed={ 3 }
        marginPagesDisplayed={ 3 }
        containerClassName='pagination'
        activeClassName='active'
        />
    );
  }
}

export default Pagination;
