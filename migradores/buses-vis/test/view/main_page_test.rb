require 'selenium-webdriver'
require 'test/unit'
require 'minitest/autorun'

# Tests done with reference to response of server.

class MainPageTest < MiniTest::Test

  @@main_page = "file:///home/melalonso/Migradores/buses-vis/views/index.html"

  def setup
    @browser = Selenium::WebDriver.for :chrome
    @browser.navigate.to @@main_page
    sleep(3) # To wait for web service initial response.
  end

  def teardown
    @browser.quit
  end

  def test_page_open
    title = @browser.find_element(:tag_name, 'h3').text
    assert_equal "Route viewer - Data Migrators", title.to_s
  end

  def test_connection_unavailable
    text = @browser.switch_to.alert.text
    @browser.switch_to.alert.accept
    assert_equal 'Something went bad', text
  end

  def test_click_route
    row = @browser.find_element(css: '#routes-table tbody tr:nth-of-type(1)')
    columns = row.find_elements(:tag_name, 'td')
    route_details = columns.last.text
    row.click
    sleep(0.3)
    route_desc = @browser.find_element(css: 'p').text
    assert_equal route_details, route_desc
  end

  def test_filter_routes_no_result
    search_box = @browser.find_element(css: '#routes-table_filter input')
    search_box.send_keys "XYZABCD"
    sleep(0.4)
    row = @browser.find_element(css: '#routes-table tbody tr:nth-of-type(1)')
    not_found = row.find_elements(:tag_name, 'td').last.text
    assert_equal not_found, "No matching records found"
  end

  def test_filter_routes_with_result
    search_box = @browser.find_element(css: '#routes-table_filter input')
    search_box.send_keys "Hatillo"
    sleep(0.4)
    row = @browser.find_element(css: '#routes-table tbody tr:nth-of-type(1)')
    place_name = row.find_elements(:tag_name, 'td').first.text
    assert_equal place_name, "Hatillo"
  end

  def test_paging_next
    row = @browser.find_element(css: '#routes-table tbody tr:nth-of-type(1)')
    details =  row.find_elements(:tag_name, 'td').last.text
    @browser.find_element(css: '#routes-table_next').click # next button
    row2 = @browser.find_element(css: '#routes-table tbody tr:nth-of-type(1)')
    details2 = row2.find_elements(:tag_name, 'td').last.text
    assert details != details2
  end

  def test_map_appears
    @browser.find_element(css: '#routes-table tbody tr:nth-of-type(1)').click # click route
    sleep(2)
    element = @browser.find_element(css: '#map') # throws exception if does not exist.
    assert !element.nil?
  end

  def test_back_to_home
    @browser.find_element(css: '#routes-table tbody tr:nth-of-type(1)').click
    sleep(1)
    @browser.find_element(css: '#back-btn').click # back button
    sleep(1)
    title_text = @browser.find_element(:tag_name, 'h3').text
    assert_equal "Route viewer - Data Migrators", title_text.to_s
  end

  def test_change_records_amount
    select = @browser.find_element(css: '#routes-table_length select')
    select.click # Click select element
    sleep(0.2)
    select.find_elements( :tag_name => "option" ).find do |option|
      option.text == "25"
    end.click
    sleep(3)
    table = @browser.find_element(css: '#routes-table tbody')
    amount = table.find_elements(:tag_name, 'tr').length
    assert amount <= 25
  end

  def test_right_amount_results
    search = @browser.find_element(css: '#routes-table_filter input')
    search.send_keys "alajuelita"
    sleep(1)
    table = @browser.find_element(css: '#routes-table tbody')
    results = table.find_elements(:tag_name, 'tr').length
    assert results == 3
  end

end
