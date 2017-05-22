import { humanize, hasError, pluck, group_by, th } from '../../javascripts/utils/utils.ts';

test('test Humanize method', () => {
  expect(humanize(null)).toBe('');
  expect(humanize(undefined)).toBe('');
  expect(humanize(" terminator_3  the rev")).toBe(' Terminator 3  The Rev');
});

test('test pluck', () => {
  var r = { a: 1, b: 1, c: 1 };
  var plucked = pluck(r, ["a", "b"]);
  expect(plucked.a).toBe(1);
  expect(plucked.b).toBe(1);
  expect(plucked.c).toBeUndefined();
});

test('test hasError', () => {

  var s = { state: { errors: [{ field: "error1", error: "a" }, { field: "error2", error: "b" }] }};
  var h = hasError.bind(s);
  expect(h("error1")).toBeTruthy();
  expect(h("")).toBeFalsy();
  expect(h("erro3")).toBeFalsy();
});

test('group_by', () => {
  var x = [{ type: "a" }, { type: "a" }, { type: "b" }];
  var objectsWithTypeA = [{ name: "bob", type: "a" }, { name: "alice", type: "a" }];
  var objectsWithTypeB = [{ name: "Ronald", type: "b" }];
  var objectsWithTypeEmpty = [{ name: "God", type: "" }];
  var objectsWithTypeUndefined = [{ name: "jan" }];
  var together = objectsWithTypeA.concat(objectsWithTypeB, objectsWithTypeEmpty, objectsWithTypeUndefined);
  var grouped = group_by(together, "type");
  expect(group_by(together, "type")).toEqual({ a: objectsWithTypeA, b: objectsWithTypeB, "": objectsWithTypeEmpty, undefined:objectsWithTypeUndefined })
})

test('th', () => {
  var thd = th("a", "b");
  var thd2 = th(null, null);
  var thd3 = th(2, 2);
  var thd4 = th(3, true);
  expect(thd).toEqual({ key: "a", label: "b" });
  expect(thd2).toEqual({ key: '', label: '' });
  expect(thd3).toEqual({ key: "2", label: "2" });
  expect(thd4).toEqual({ key: "3", label: "true" });
})
