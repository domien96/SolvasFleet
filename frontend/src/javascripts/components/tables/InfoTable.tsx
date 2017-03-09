import React      from 'react';
import T          from 'i18n-react';
//import classNames from 'classnames';


export default class InfoTable extends React.Component<InfoTableProps, {}> {
  
  constructor(){
    super();
    this.doeKlik = this.doeKlik.bind(this);
  }

  doeKlik(){
    console.log("klikje")
  }


  public getRows() : any {
    const head = this.props.head;
    const data = this.props.data;

    return data.map((item : any, i : number) => {
      const cells = head.map((headData : any, j : number) => {
        return (
          <td key={ j }>{item[headData.key]}</td>
        );
      });
      return (
        <tr key={ i } onClick={() => this.props.onClick({item})} className='table-row'>{cells}</tr>
      );
    });
  }

  render() {

    const tableHead = this.props.head.map((headData : any) => 
    (
      <th key={headData.key} scope='row' className='table-row' >{T.translate(headData.label)}</th>
    ));

    const tableRows = this.getRows();

    return (
      <div className='table-wrap'>
      <table className='table table-striped table-hover'>
        <thead className='thead-default'>
          <tr>{tableHead}</tr>
        </thead>
        <tbody>
          {tableRows}
        </tbody>
      </table>
      </div>
    );
  }
}
