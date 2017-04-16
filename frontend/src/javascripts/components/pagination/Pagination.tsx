import React from 'react';
import { ButtonGroup, DropdownButton, MenuItem,Button } from 'react-bootstrap';


interface Props {
  onClick:any
}

interface State {
  query:PaginationQuery
}

const PageButton = ({ click ,n}) => {
  return (
    <div className="col-sm-4">
    <button onClick={ ()=>click(n) } className='btn btn-default form-control'>
       {n}
    </button>
    </div>
  );
}

class Pagination extends React.Component<Props, State> {
  constructor() {
    super();
    this.state = {
      query: { limit:20, page:0 }
    };
    this.clickPage=this.clickPage.bind(this)
    this.setLimit=this.setLimit.bind(this)
  }

  clickPage(page:number) {
    this.state.query.page=page
    this.props.onClick(this.state.query)
  }

  setLimit(limit) {
    this.state.query.limit=limit
    this.props.onClick(this.state.query)
  }

  render() {
    return (
      <div className='row action'>
      <PageButton click={this.clickPage} n='0'/>
      <PageButton click={this.clickPage} n='1'/>
      <ButtonGroup>
        <DropdownButton onSelect={this.setLimit} title="Elements per page" id="bg-nested-dropdown">
          <MenuItem eventKey="25">25</MenuItem>
          <MenuItem eventKey="50">50</MenuItem>
          <MenuItem eventKey="100">100</MenuItem>
        </DropdownButton>
      </ButtonGroup>
      </div>
    );
  }

}

export default Pagination;
