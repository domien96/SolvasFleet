import React from 'react';
import { ButtonGroup, DropdownButton, MenuItem} from 'react-bootstrap';

interface Props {
  onClick: any;
  response: PaginationResponse;
}

interface State {
  query: PaginationQuery;
}

const PageButton = ({ click, n}: { click: any, n: any }) => {
  return (
    <div className='col-sm-4'>
      <button onClick={ () => click(n) } className='btn btn-default form-control'>
        {n}
      </button>
    </div>
  );
};

class Pagination extends React.Component<Props, State> {
  constructor() {
    super();
    this.state = {
      query: { limit: 20, page: 0 },
    };
    this.clickPage = this.clickPage.bind(this);
    this.setLimit = this.setLimit.bind(this);
  }

  clickPage(page: number) {
    this.state.query.page = page;
    this.props.onClick(this.state.query);
  }

  setLimit(limit: number) {
    this.state.query.limit = limit;
    this.props.onClick(this.state.query);
  }

  render() {
    const indents = [];
    const times = Math.ceil(this.props.response.total / this.props.response.limit);
    if (times > 1) {
      for (let i = 0; i < times; i++) {
        indents.push(<PageButton key={ i } click={ this.clickPage } n={ i } />);
      }
    }

    if (this.props.response.total > 20) {
      return (
        <div className='row action'>
          {indents}
          <ButtonGroup>
            <DropdownButton onSelect={this.setLimit} title='Elements per page' id='bg-nested-dropdown'>
              <MenuItem eventKey='25'>25</MenuItem>
              <MenuItem eventKey='50'>50</MenuItem>
              <MenuItem eventKey='100'>100</MenuItem>
            </DropdownButton>
          </ButtonGroup>
        </div>
      );
    } else {
      return null;
    }
  }
}

export default Pagination;
