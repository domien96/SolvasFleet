interface LogEntryData {
  id?: number;
  entity?: number;
  entityType?: string;
  logDate?: string;
  method?: string;
  user?: number;
  payload?: any;
}

interface LogFilterData {
  [after: string]: string;
	[before: string]: string;
	[method: string]: string;
  [entityType: string]: string;
  [user: string]: string;
}