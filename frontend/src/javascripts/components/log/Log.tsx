import React from 'react';
import Layout from './Layout.tsx';
import { fetchLog } from '../../actions/audit_actions.ts';
import { redirect_to } from '../../routes/router.tsx';

interface State {
  response: ListResponse;
}

class Log extends React.Component<{}, State> {
  constructor(props: {}) {
    super(props);
    this.state = { response: {
      data: [],
      first: null,
      last: null,
      limit: 0,
      next: null,
      offset: 0,
      previous: null,
      total: 0,
    } };
    this.fetchLog = this.fetchLog.bind(this);
  }

  componentDidMount() {
    this.fetchLog();
  }

  fetchLog(query?: any) {
    fetchLog((data: any) => this.setState({ response: data }), undefined, query);
  }

  handleClick(id: number) {
    redirect_to(`/log/${id}`);
  }

  render() {
    const children = React.Children.map(this.props.children, (child: any) => React.cloneElement(child, {
      fetchLog: this.fetchLog.bind(this),
    }));

    return (
      <Layout response={this.state.response} onLogSelect={ this.handleClick } fetchLog={this.fetchLog} >
        { children }
      </Layout>
    );
  }
}

export default Log;
