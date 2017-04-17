import React from 'react';
import { connect } from 'react-redux';
import classNames from 'classnames';

import { changeLanguage } from '../../actions/action_creators.ts';
import { State as AppState } from '../../reducers/app.ts';
import { languages } from '../../i18n.ts';

interface Props {
  lang: string;
  change: (lang: string) => void;
}

interface State {
  visible: boolean;
}

class LanguageSwitcher extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = { visible: false };
    this.toggleVisible = this.toggleVisible.bind(this);
  }

  toggleVisible() {
    this.setState({ visible: !this.state.visible });
  }

  chooseLang(lang: string) {
    return () => {
      this.setState({ visible: false });
      this.props.change(lang);
    }
  }

  render () {
    let { visible } = this.state;
    let { lang } = this.props;

    const options = Object.keys(languages).map((l, i) => {
      return (
        <span key={ i } className='click' onClick={ this.chooseLang(l) }>
          { l }
        </span>
      );
    });

    return (
      <div className={ classNames('lang-wrapper', { active: visible })}>
        <div className='click' onClick={ this.toggleVisible }>
          Language:
          <span className='current'>
            { lang }
          </span>
        </div>
        <div className='options'>
          { options }
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ lang }: AppState) => {
  return { lang };
}

const mapDispatchToProps = (dispatch: any) => {
  return {
    change: (lang: string) => dispatch(changeLanguage(lang))
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LanguageSwitcher);
