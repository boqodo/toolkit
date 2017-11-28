select y1.* from (
select datatype,
       month,
       day,
       summary,
       cremoney,
       debmoney,
       balancedir,
       voucherno,
       case
         when datatype = 3 or datatype = 4 then
          balancemoney
         else
          sum(money) over(order by month, datatype, voucherno,line)
       end balancemoney,
       voucherguid,line,trancode
  from (select datatype,
               month,
               day,
               summary,
               cremoney,
               debmoney,
               balancedir,
               voucherno,
               case
                 when datatype = 3 or datatype = 4 then
                  0
                 else
                  balancemoney
               end money,
               balancemoney,
               voucherguid,line,trancode
          from (select 3 as datatype,
                       t.month,
                       '' day,
                       '本月合计' as summary,
                       sum(t.bycremoney) cremoney,
                       sum(t.bydebmoney) debmoney,
                       t.balancedir,
                       sum(t.balancemoney) balancemoney,
                       999999 voucherno,
                       0 voucherguid,0 line,'' trancode
                  from pzlr_subjectbalance t
                 where t.sectionguid = ?
                   and t.setid = ?
                   and t.month >= ?
                   and t.month <= ?
                   and t.gentype = 1
                   and t.balancetype = 0
                   and t.year =?
                   and t.enterguid = decode(?,0,t.enterguid,?)
                 group by t.balancedir, t.month
                union all
                select 4 as datatype,
                       t.month,
                       '' day,
                       '本月累计' as summary,
                       sum(t.cremoney) cremoney,
                       sum(t.debmoney) debmoney,
                       t.balancedir,
                       sum(t.balancemoney) balancemoney,
                       999999 voucherno,
                       0 voucherguid,0 line,'' trancode
                  from pzlr_subjectbalance t
                 where t.sectionguid = ?
                   and t.setid = ?
                   and t.month >= ?
                   and t.month <= ?
                   and t.gentype = 1
                    and t.year =?
                   and t.balancetype = 0
                   and t.enterguid = decode(?,0,t.enterguid,?)
                 group by t.balancedir, t.month
                union all
                select 2 as datatype,
                       v.month,
                       to_char(v.day),
                       '' summary,
                       sum(d.cremoney) cremoney,
                       sum(d.debmoney) debmoney,
                       s.balancedir,
                       sum(decode(s.balancedir,
                                  'D',
                                  d.debmoney - d.cremoney,
                                  d.cremoney - d.debmoney)) balancemoney,
                       v.voucherno,
                       v.guid voucherguid,d.line,v.trancode
                  from pzlr_voucher v, pzlr_voucherdetail d, xtsz_section s
                 where v.guid = d.voucherguid
                   and d.sectionguid = s.guid
                   and d.sectionincode  like ? || '%'
                   and v.setid = ?
                   and s.setid = ?
                   and v.month >= ?
                   and v.month <= ?
                    and v.year =?
                   and v.enterguid = decode(?,0,v.enterguid,?)
                   and v.status = '2'
                 group by v.month, v.voucherno,v.guid,d.line,v.day, s.balancedir,v.trancode)
			 union all
   select 1 as datatype,
                       to_number(max(month)) as month,
                       '' day,
                       decode(?, 1, '上年结转', '上期结转') as summary,
                       sum(ts.cremoney) as cremoney,
                       sum(ts.debmoney) as debmoney,
                       ts.balancedir,
                        999999 voucherno,
						sum(ts.balancemoney) as money,
                      sum(ts.balancemoney) as balancemoney,
                       0 voucherguid,0 line,'' trancode
                  from (select 1 as datatype,
                               0 as month,
                               decode(?, 1, '上年结转', '上期结转') as summary,
                               0 as cremoney,
                               0 as debmoney,
                               t.balancedir,
                               0 as balancemoney
                          from xtsz_section t
                         where t.guid = ? 
                                   and t.setid = ?
                        union all
                        select 1 as datatype,
                               t.month,
                               decode(?, 1, '上年结转', '上期结转') as summary,
                               t.cremoney,
                               t.debmoney,
                               t.balancedir,
                               t.balancemoney
                          from pzlr_subjectbalance t
                         where t.enterguid = decode(?,0,t.enterguid,?)
                           and t.sectionguid = ?
                           and t.setid = ?
                           and t.year =?
                           and t.gentype = 0
                           and t.balancetype = 0) ts
                 group by ts.balancedir
	 
			)
                 ) y1 ,(
    select t.month
  from pzmx_ydz_view t
 where t.sectionincode like ? || '%'
   and t.month >= ?
   and t.month <= ?
   and t.setid = ?
   and t.enterguid = decode(?,0,t.enterguid,?) 
   group by t.month 
   ) y2 where y1.month = y2.month 
  