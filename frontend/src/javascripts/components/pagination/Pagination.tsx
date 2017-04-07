import React from 'react';

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
      query: { limit:20, page:1 }
    };
    this.clickPage=this.clickPage.bind(this)
  }

  clickPage(page:number) {
    this.state.query.page=page
    this.props.onClick(this.state.query)
  }

  render() {
    return (
      <div className='row action'>
      <PageButton click={this.clickPage} n='0'/>
      <PageButton click={this.clickPage} n='1'/>
      <PageButton click={this.clickPage} n='2'/>
      </div>
    );
  }

}

export default Pagination;
