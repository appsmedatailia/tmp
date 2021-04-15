import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/entreprise">
      Entreprise
    </MenuItem>
    <MenuItem icon="asterisk" to="/manager">
      Manager
    </MenuItem>
    <MenuItem icon="asterisk" to="/consultant">
      Consultant
    </MenuItem>
    <MenuItem icon="asterisk" to="/skill-proficiency">
      Skill Proficiency
    </MenuItem>
    <MenuItem icon="asterisk" to="/consultant-preference">
      Consultant Preference
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
